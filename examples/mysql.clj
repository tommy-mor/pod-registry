(require '[babashka.pods :as pods])

(pods/load-pod 'org.babashka/mysql "0.0.7")

(require '[pod.babashka.mysql :as mysql])

;; start mysql: docker run --rm --name=test-mysql -p 3306:3306 -i --env="MYSQL_ROOT_PASSWORD=mypassword" mysql

(def conn {:dbtype "mysql"
           :user "root"
           :password "mypassword"})

(mysql/execute! conn ["drop database if exists mydb;"])
(mysql/execute! conn ["create database if not exists mydb;"])

(def db (assoc conn :dbname "mydb"))

(mysql/execute! db ["create table mytable ( foobar int );"])
(mysql/execute! db ["insert into mytable (foobar) values (3);"])
(mysql/execute! db ["select * from mytable"])
;;=> [{:mytable/foobar 3}]
