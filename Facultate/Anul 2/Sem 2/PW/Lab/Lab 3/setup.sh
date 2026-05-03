#!/bin/bash
gcc demo.c -o demo.cgi
gcc show_input_data.c -o show_input_data.cgi
gcc show_input_data2.c -o show_input_data2.cgi
gcc show_input_data3.c -o show_input_data3.cgi

sudo cp *.cgi /usr/lib/cgi-bin/
sudo chmod +x /usr/lib/cgi-bin/*.cgi
sudo cp *.html /var/www/html/
echo "0" | sudo tee /usr/lib/cgi-bin/contor.txt
sudo chmod 666 /usr/lib/cgi-bin/contor.txt
sudo service apache2 restart
