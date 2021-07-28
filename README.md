# AndroidSQLInjection
SQL 注入DEMO
万能登录钥匙

- `username' or '1'='1`

- Sql 中 `and` 优先级比 `or` 高

  ```sqlite
  select *from user where name='sinyu' or '1'='1'and psw='1wry'
  等价于：
  select *from user where name='sinyu' or ('1'='1'and psw='1wry')
  ```

- 如果不知道 用户名怎么办？用 `xxx' or '1'='1'--`

  ```sqlite
  select *from user where name='xxx' or '1'='1'  --'and psw='syih'
  ```

- 解决方案

  - 使用 占位符？，将 `username' or '1=1` 作为一个整体
  - 更加安全的第三方库等
