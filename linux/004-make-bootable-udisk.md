## 制作U盘启动盘
1.查看挂载的U盘
```shell
[jimo@jimo-pc ~]$ sudo fdisk -l
[sudo] jimo 的密码：
Disk /dev/sda：223.6 GiB，240057409536 字节，468862128 个扇区
单元：扇区 / 1 * 512 = 512 字节
扇区大小(逻辑/物理)：512 字节 / 512 字节
I/O 大小(最小/最佳)：512 字节 / 512 字节
磁盘标签类型：gpt
磁盘标识符：1C0C5B08-4489-429F-9226-660849ADF8D2

设备            起点      末尾      扇区   大小 类型
/dev/sda1       4097    618497    614401   300M EFI 系统
/dev/sda2     618498 450402086 449783589 214.5G Linux 文件系统
/dev/sda3  450402087 468857024  18454938   8.8G Linux swap

Disk /dev/sdc：7.5 GiB，8054112256 字节，15730688 个扇区
单元：扇区 / 1 * 512 = 512 字节
扇区大小(逻辑/物理)：512 字节 / 512 字节
I/O 大小(最小/最佳)：512 字节 / 512 字节
磁盘标签类型：dos
磁盘标识符：0x2cde6d13

设备       启动  起点     末尾     扇区  大小 Id 类型
/dev/sdc1  *    16128 15730687 15714560  7.5G  c W95 FAT32 (LBA)
```
2.umount该U盘
```shell
[jimo@jimo-pc ~]$ sudo umount /dev/sdc1
```
3.格式化为fat格式：
```shell
[jimo@jimo-pc ~]$ sudo mkfs.vfat /dev/sdc1 -I
mkfs.fat 4.1 (2017-01-24)
```
4.写入iso文件，这一步可能花很长时间，取决于U盘速度
```shell
[jimo@jimo-pc ~]$ sudo dd if=/run/media/jimo/寂寞的U/virtualbox/iso/deepin-15.3-amd64.iso of=/dev/sdc1
记录了4684440+0 的读入
记录了4684440+0 的写出
2398433280 bytes (2.4 GB, 2.2 GiB) copied, 1043.71 s, 2.3 MB/s
```