from random import randint, randrange
import datetime

def random_date(start, end):
    """
    This function will return a random datetime between two datetime
    objects.
    """
    delta = end - start
    int_delta = (delta.days * 24 * 60 * 60) + delta.seconds
    random_second = randrange(int_delta)
    return start + datetime.timedelta(seconds=random_second)


station_count = 20
user_count = 1000
bike_count = 200

filename = "Insert_rental.txt"
file = open(filename, "w")
insert_string = "INSERT INTO public.rentals(rental_id, user_id, bike_id, rental_date, return_date, price) "

for i in range(0, 4000):
    d1 = datetime.datetime(randint(2019, 2020), randint(1, 12), randint(1, 28), randint(6, 23), randint(0, 59), 0)
    d2 = d1 + datetime.timedelta(seconds=randint(240, 900))
    rental_id = "DEFAULT"
    user_id = i % user_count + 1
    bike_id = i % bike_count + 1
    rental_date = "\'" + d1.__str__() + "\'"
    return_date = "\'" + d2.__str__() + "\'"
    price = randint(1, 15) + randint(1, 99) / 100
    values_string = "VALUES(%s, %s, %s, %s, %s, %s);\n" % (rental_id, user_id, bike_id, rental_date, return_date, price)
    file.write(insert_string + values_string)


file.close()