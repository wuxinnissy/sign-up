### Database document
> 2024-01-03 12:18:12
#### checkin_case  
NO | KEY | COLUMN | COMMENT | DATA_TYPE | NOTNULL | REMARK
:---: | :---: | --- | --- | --- | :---: | ---
1|PRI|checkin_id| |INT|Y|
2| |create_time| |DATETIME|N|
3| |valid_time| |INT|N|
4| |course_id| |VARCHAR(20)|N|
5| |teacher_id| |VARCHAR(20)|N|
6| |checkin_node| |VARCHAR(8)|N|
#### checkin_record  
NO | KEY | COLUMN | COMMENT | DATA_TYPE | NOTNULL | REMARK
:---: | :---: | --- | --- | --- | :---: | ---
1|PRI|checkin_id| |INT|Y|
2|PRI|student_id| |VARCHAR(20)|Y|
3| |checkin_time| |DATETIME|N|
4| |checkin_status| |SMALLINT|N|
#### course  
NO | KEY | COLUMN | COMMENT | DATA_TYPE | NOTNULL | REMARK
:---: | :---: | --- | --- | --- | :---: | ---
1|PRI|course_id| |VARCHAR(20)|Y|
2| |course_name| |VARCHAR(100)|N|
3| |begin_time| |DATETIME|N|
4| |end_time| |DATETIME|N|
#### student  
NO | KEY | COLUMN | COMMENT | DATA_TYPE | NOTNULL | REMARK
:---: | :---: | --- | --- | --- | :---: | ---
1|PRI|student_id| |VARCHAR(32)|Y|
2| |student_name| |VARCHAR(32)|Y|
3| |password| |VARCHAR(32)|Y|
#### student_course  
NO | KEY | COLUMN | COMMENT | DATA_TYPE | NOTNULL | REMARK
:---: | :---: | --- | --- | --- | :---: | ---
1| |student_id| |VARCHAR(20)|N|
2| |course_id| |VARCHAR(20)|N|
#### teacher  
NO | KEY | COLUMN | COMMENT | DATA_TYPE | NOTNULL | REMARK
:---: | :---: | --- | --- | --- | :---: | ---
1|PRI|teacher_id| |VARCHAR(32)|Y|
2| |teacher_name| |VARCHAR(32)|Y|
3| |password| |VARCHAR(32)|Y|
#### teacher_course  
NO | KEY | COLUMN | COMMENT | DATA_TYPE | NOTNULL | REMARK
:---: | :---: | --- | --- | --- | :---: | ---
1| |teacher_id| |VARCHAR(20)|N|
2| |course_id| |VARCHAR(20)|N|
