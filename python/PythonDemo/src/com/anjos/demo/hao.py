import pymssql as ps

conn=ps.connect(host=".", database="LKKData",user="sa",password="200881037")
cur=conn.cursor()
cur.execute("select CommentId,rateContent from Comments")
row=cur.fetchone()
for i in range(2):
    print row[0],row[1]
    row=cur.fetchone()
conn.close()
