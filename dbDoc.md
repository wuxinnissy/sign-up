### Database document
> 2023-12-28 17:19:00
#### checkin_case  
NO | KEY | COLUMN | COMMENT | DATA_TYPE | NOTNULL | REMARK
:---: | :---: | --- | --- | --- | :---: | ---
1|PRI|checkin_id| |INT|Y|
2| |create_time| |DATETIME|N|
3| |course_id| |VARCHAR(20)|N|
4| |teacher_id| |VARCHAR(20)|N|
5| |checkin_node| |VARCHAR(8)|N|
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
1|PRI|student_id| |VARCHAR(20)|Y|
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
1|PRI|teacher_id| |VARCHAR(20)|Y|
2| |course_id| |VARCHAR(20)|N|
