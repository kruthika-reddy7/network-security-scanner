from flask import Flask, render_template, request, g
import nmap
import socket
from threading import Thread, Lock
import time

app = Flask(__name__)

class NetworkScanner:
    _instance = None
    _lock = Lock()

    def __new__(cls):
        with cls._lock:
            if cls._instance is None:
                cls._instance = super().__new__(cls)
                cls._instance._initialize()
        return cls._instance

    def _initialize(self):
        self.scanner = nmap.PortScanner()
        self.results = []
        self.scanning = False
        self.progress = 0
        self.last_scan_time = 0
        self.scan_lock = Lock()

    def scan_network(self, ip_range='192.168.1.0/24'):
        with self.scan_lock:
            self.scanning = True
            self.progress = 0
            self.results = []
            
            try:
                print(f"Scanning network {ip_range}...")
                self.scanner.scan(hosts=ip_range, arguments='-sS -O -F')
                self.progress = 50
                
                for host in self.scanner.all_hosts():
                    if self.scanner[host].state() == 'up':
                        ip = host
                        hostname = self._get_hostname(ip)
                        status = 'up'
                        os = self._get_os(host)
                        ports = self._get_open_ports(host)
                        
                        self.results.append({
                            'ip': ip,
                            'hostname': hostname,
                            'status': status,
                            'os': os,
                            'ports': ports
                        })
                
                self.progress = 100
                self.last_scan_time = time.time()
            except Exception as e:
                print(f"Scan failed: {str(e)}")
                self.results = []
            finally:
                self.scanning = False

    def _get_hostname(self, ip):
        try:
            return socket.gethostbyaddr(ip)[0]
        except (socket.herror, socket.gaierror):
            return ""

    def _get_os(self, host):
        try:
            os_info = self.scanner[host]['osmatch']
            if os_info:
                return os_info[0]['name']
        except KeyError:
            pass
        return ""

    def _get_open_ports(self, host):
        ports = []
        try:
            for proto in self.scanner[host].all_protocols():
                port_info = self.scanner[host][proto]
                for port, state in port_info.items():
                    if state['state'] == 'open':
                        service = state['name']
                        ports.append(f"{port}/{service}")
        except KeyError:
            pass
        return ", ".join(ports)

# Singleton instance
scanner = NetworkScanner()

@app.before_request
def before_request():
    # Ensure scanner is properly initialized for each request
    if not hasattr(scanner, 'results'):
        scanner._initialize()

@app.route('/', methods=['GET', 'POST'])
def index():
    if request.method == 'POST':
        ip_range = request.form.get('ip_range', '192.168.1.0/24')
        if not scanner.scanning:
            thread = Thread(target=scanner.scan_network, args=(ip_range,))
            thread.start()
        return render_template('scanning.html')
    
    return render_template('index.html', results=scanner.results, scanning=scanner.scanning)

@app.route('/results')
def results():
    return render_template('results.html', results=scanner.results)

@app.route('/status')
def status():
    return {
        'scanning': scanner.scanning,
        'progress': scanner.progress,
        'results_available': len(scanner.results) > 0
    }

if __name__ == '__main__':
    app.run(debug=False)  
     # Disable debug to prevent multiple instances