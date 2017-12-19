查看系统的一些信息.

## 1.查看内存型号
当需要换内存时需要
```shell
[jimo@jimo-pc ~]$ sudo dmidecode -t memory
# dmidecode 3.1
Getting SMBIOS data from sysfs.
SMBIOS 2.8 present.

Handle 0x0046, DMI type 16, 23 bytes
Physical Memory Array
	Location: System Board Or Motherboard
	Use: System Memory
	Error Correction Type: None
	Maximum Capacity: 16 GB
	Error Information Handle: Not Provided
	Number Of Devices: 2

Handle 0x0047, DMI type 17, 40 bytes
Memory Device
	Array Handle: 0x0046
	Error Information Handle: Not Provided
	Total Width: 64 bits
	Data Width: 64 bits
	Size: 4096 MB
	Form Factor: SODIMM
	Set: None
	Locator: DIMM A
	Bank Locator: BANK 0
	Type: DDR3
	Type Detail: Synchronous
	Speed: 1600 MT/s
	Manufacturer: Samsung
	Serial Number: 12151215
	Asset Tag: 9876543210
	Part Number: M471B5173QH0-YK0  
	Rank: 1
	Configured Clock Speed: 1600 MT/s
	Minimum Voltage: Unknown
	Maximum Voltage: Unknown
	Configured Voltage: 1.35 V
...
```
## 2.查看磁盘使用情况
```shell
[jimo@jimo-pc ~]$ df
文件系统        容量  已用  可用 已用% 挂载点
dev             5.9G     0  5.9G    0% /dev
run             5.9G  1.4M  5.9G    1% /run
/dev/sda2       211G   77G  124G   39% /
tmpfs           5.9G   79M  5.8G    2% /dev/shm
tmpfs           5.9G     0  5.9G    0% /sys/fs/cgroup
tmpfs           5.9G   44M  5.8G    1% /tmp
/dev/sda1       300M  256K  300M    1% /boot/efi
tmpfs           1.2G   16K  1.2G    1% /run/user/1000
```
也可以使用di命令，不过需要安装。
## 3.测试硬盘读写速度
写：
```shell
[jimo@jimo-pc ~]$ dd if=/dev/zero of=/tmp/output.img bs=8k count=256k conv=fdatasync; rm -rf /tmp/output.img
记录了262144+0 的读入
记录了262144+0 的写出
2147483648 bytes (2.1 GB, 2.0 GiB) copied, 0.591138 s, 3.6 GB/s
```
读：
```shell
[jimo@jimo-pc ~]$ sudo hdparm -Tt /dev/sda
[sudo] jimo 的密码：

/dev/sda:
 Timing cached reads: 17230 MB in  2.00 seconds = 8622.86 MB/sec
 Timing buffered disk reads: 978 MB in  3.01 seconds = 325.39 MB/sec
```
看起来SSD还不错。