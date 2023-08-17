# WurmUnlimitedPrometheusStatisticsExporter
This tool exports statistics intended for consumption by Prometheus.
## Configuration
You can modify the following settings in the configuration file:
* serverPort= (Default: 5315)
Please be aware that on Linux operating systems, ports below 1024 will not function as they necessitate root permissions for binding.
* serverAddress= Default: 127.0.0.1)

Note: It is not advisable to expose the exporter to a public IP address.
Note: On linux OS'es ports bellow 1024 will not work as they require root permissions to bind to.
## Output

```
# HELP wu_number_of_creatures_total The total number of creatures. 
# TYPE wu_number_of_creatures_total counter 
wu_number_of_creatures_total{type="agro"} 1000
wu_number_of_creatures_total{type="nice"} 1000
```