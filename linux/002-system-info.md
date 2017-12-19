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