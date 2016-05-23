This application asks the user for an IP address, and then a range of ports to scan. The application then scans each port withing range at the given IP address. When done the application will show which ports are open.

added concurrency to this application. the application now run 2 seperate threads. the first thread scans the first half of the port ranges, and the second half of the port ranges is scanned by the second thread.

This project help me learn more of working with concurrency in java. This project also helped me learn more about using regular expressions.
