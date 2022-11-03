# RemotePC_Socket
## Mô tả hệ thống
### Nguyên lý hoạt động hệ thống
Hệ thống gồm 2 phần:
- Server
- Client
Cách hoạt động:
- client sẽ chụp ảnh màn hình liên tục (chụp bằng lớp Robot trong java,chụp theo size của màn hình client-chụp toàn bộ màn hình) và gửi cho server (gửi dạng ImageIcon bằng phương thức writeObject)
- truyền thông bằng Socket: gửi nhận ảnh, gửi nhận các command điều khiển của server(xử lý Control trên máy khách)
- server nhận ảnh chụp màn hình từ client dưới dạng ImageIcon và vẽ lại bằng Graphics  của Jpanel(công cụ Javax.swing) để hiển thị 
- Server gửi các command điều khiển xuống client , các command dạng int quy định các lệnh (biểu diễn trong file EnumCommands.java) – gửi bằng PrintWriter
- Client nhận các command dạng int qua getInputStream
