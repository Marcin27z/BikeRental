import string
import coolname
import random

filename = "Insert_Station.txt"
file = open(filename, "w")
insert_string = "INSERT INTO public.stations(station_id, address, deleted, lat, lng) "

min_lat = 52.20255705
min_lon = 20.97135544
lat_range = 0.08471835
lon_range = 0.10368347

for i in range(0, 20):
    station_id = "DEFAULT"
    deleted = "\'" + "false" + "\'"
    address = "\'" + coolname.generate_slug(3) + " " + random.randint(1, 25).__str__() + "\'"
    lat = min_lat + random.uniform(0, lat_range)
    lon = min_lon + random.uniform(0, lon_range)
    default = "DEFAULT"
    values_string = "VALUES(%s, %s, %s, %s, %s);\n" % (station_id, address, deleted, lat, lon)
    file.write(insert_string + values_string)


file.close()