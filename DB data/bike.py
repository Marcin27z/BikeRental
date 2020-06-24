import string
import coolname
import random

station_count = 20

filename = "Insert_Bike.txt"
file = open(filename, "w")
insert_string = "INSERT INTO public.bikes(bike_id, station_id, name, status) "
models = []
models.append(coolname.generate_slug(2))
models.append(coolname.generate_slug(2))
models.append(coolname.generate_slug(2))
models.append(coolname.generate_slug(2))

for i in range(0, 200):
    bike_id = "DEFAULT"
    station_id = (i % station_count) + 1
    name = "\'" + random.choice(models) + "\'"
    status = "DEFAULT"
    values_string = "VALUES(%s, %s, %s, %s);\n" % (bike_id, station_id, name, status)
    file.write(insert_string + values_string)


file.close()