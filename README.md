简易用户管理系统
============
# 功能
1. http://localhost:8000/user/list 查看全部用户，未做分页。
2. 可以通过用户id和登录名查询，登录名支持模糊搜索，用户ID不支持。考虑到ID上有索引，通过like就没法利用索引了，可以通过b-tree做成左匹配搜索机制。
3. 查询结果可以通过选择排序，按查询后，结果集排序，默认用户ID正序。
4. 增加用户：http://localhost:8000/user/add?loginName=Arace&name=Arace&password=&userId=
5. 删除用户：http://localhost:8000/user/delete?userId=5
6. 修改用户：http://localhost:8000/user/add?userId=1&loginName=Haha&name=123&password=123


