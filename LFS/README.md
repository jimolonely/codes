# LFS
版本：8.1
# 准备
## 安装系统
安装个ubuntu17.04的虚拟机。

## 准备硬件环境
### 给虚拟机分配磁盘创建新分区
直接在设置里分配，我们就在这个盘上构建系统，这里用10G
```shell
jimo@jimo-VirtualBox:~$ sudo fdisk -l
Disk /dev/sda: 20 GiB, 21474836480 bytes, 41943040 sectors
Units: sectors of 1 * 512 = 512 bytes
Sector size (logical/physical): 512 bytes / 512 bytes
I/O size (minimum/optimal): 512 bytes / 512 bytes
Disklabel type: dos
Disk identifier: 0xd4b51953

Device     Boot Start      End  Sectors Size Id Type
/dev/sda1  *     2048 41940991 41938944  20G 83 Linux


Disk /dev/sdb: 10 GiB, 10737418240 bytes, 20971520 sectors
Units: sectors of 1 * 512 = 512 bytes
Sector size (logical/physical): 512 bytes / 512 bytes
I/O size (minimum/optimal): 512 bytes / 512 bytes
```
现在把/dev/sdb创建一个新分区：
```shell
jimo@jimo-VirtualBox:~$ sudo fdisk /dev/sdb

Welcome to fdisk (util-linux 2.29).
Changes will remain in memory only, until you decide to write them.
Be careful before using the write command.

Device does not contain a recognized partition table.
Created a new DOS disklabel with disk identifier 0xa563b5cb.

Command (m for help): m

Help:

  DOS (MBR)
   a   toggle a bootable flag
   b   edit nested BSD disklabel
   c   toggle the dos compatibility flag

  Generic
   d   delete a partition
   F   list free unpartitioned space
   l   list known partition types
   n   add a new partition
   p   print the partition table
   t   change a partition type
   v   verify the partition table
   i   print information about a partition

  Misc
   m   print this menu
   u   change display/entry units
   x   extra functionality (experts only)

  Script
   I   load disk layout from sfdisk script file
   O   dump disk layout to sfdisk script file

  Save & Exit
   w   write table to disk and exit
   q   quit without saving changes

  Create a new label
   g   create a new empty GPT partition table
   G   create a new empty SGI (IRIX) partition table
   o   create a new empty DOS partition table
   s   create a new empty Sun partition table


Command (m for help): n
Partition type
   p   primary (0 primary, 0 extended, 4 free)
   e   extended (container for logical partitions)
Select (default p): p
Partition number (1-4, default 1): 1
First sector (2048-20971519, default 2048): 
Last sector, +sectors or +size{K,M,G,T,P} (2048-20971519, default 20971519): 

Created a new partition 1 of type 'Linux' and of size 10 GiB.

Command (m for help): i
Selected partition 1
         Device: /dev/sdb1
          Start: 2048
            End: 20971519
        Sectors: 20969472
      Cylinders: 1306
           Size: 10G
             Id: 83
           Type: Linux
    Start-C/H/S: 0/32/33
      End-C/H/S: 281/106/17

Command (m for help): w
The partition table has been altered.
Calling ioctl() to re-read partition table.
Syncing disks.
```
现在就有了：
```shell
jimo@jimo-VirtualBox:~$ sudo fdisk -l
Disk /dev/sda: 20 GiB, 21474836480 bytes, 41943040 sectors
Units: sectors of 1 * 512 = 512 bytes
Sector size (logical/physical): 512 bytes / 512 bytes
I/O size (minimum/optimal): 512 bytes / 512 bytes
Disklabel type: dos
Disk identifier: 0xd4b51953

Device     Boot Start      End  Sectors Size Id Type
/dev/sda1  *     2048 41940991 41938944  20G 83 Linux


Disk /dev/sdb: 10 GiB, 10737418240 bytes, 20971520 sectors
Units: sectors of 1 * 512 = 512 bytes
Sector size (logical/physical): 512 bytes / 512 bytes
I/O size (minimum/optimal): 512 bytes / 512 bytes
Disklabel type: dos
Disk identifier: 0xa563b5cb

Device     Boot Start      End  Sectors Size Id Type
/dev/sdb1        2048 20971519 20969472  10G 83 Linux
```
### 创建ext4文件系统
给分区格式化为ext4文件系统
```shell
jimo@jimo-VirtualBox:~$ sudo mkfs -v -t ext4 /dev/sdb1
mke2fs 1.43.4 (31-Jan-2017)
fs_types for mke2fs.conf resolution: 'ext4'
Filesystem label=
OS type: Linux
Block size=4096 (log=2)
Fragment size=4096 (log=2)
Stride=0 blocks, Stripe width=0 blocks
655360 inodes, 2621184 blocks
131059 blocks (5.00%) reserved for the super user
First data block=0
Maximum filesystem blocks=2151677952
80 block groups
32768 blocks per group, 32768 fragments per group
8192 inodes per group
Filesystem UUID: 8e9c7176-6a2c-46cb-bdc1-d6950884a95a
Superblock backups stored on blocks: 
	32768, 98304, 163840, 229376, 294912, 819200, 884736, 1605632

Allocating group tables: done                            
Writing inode tables: done                            
Creating journal (16384 blocks): done
Writing superblocks and filesystem accounting information: done 
```
## 环境变量与挂载
因为到处都要用到/mnt/lfs这个地址，所以追加为变量。
放到~/.bashrc下：
```shell
export LFS=/mnt/lfs
```
然后将硬盘挂载到该文件夹：
```shell
jimo@jimo-VirtualBox:~$ echo $LFS
/mnt/lfs
jimo@jimo-VirtualBox:~$ sudo mkdir -pv $LFS
[sudo] password for jimo: 
mkdir: created directory '/mnt/lfs'
jimo@jimo-VirtualBox:~$ sudo mount -v -t ext4 /dev/sdb1 $LFS
mount: /dev/sdb1 mounted on /mnt/lfs.
```
硬件环境搞定。

**为了避免每次登录都需要重新挂载分区，使用以下方式自动挂载：**
编辑/etc/fstab文件，追加：
```shell
/dev/sdb1 /mnt/lfs defaults 0 0
```
最后几个参数的意思：
```shell
第四字段:挂载参数
第五字段:指定分区是否被dump备份,0代表不备份,1代表每天备份,2代表不定期备份
第六字段:指定分区是否被fsck检测,0代表不检测,其它数字代表检测的优先级,1的优先级比2高
```

## 准备软件包
我们需要的 一切都在这里：[http://www.linuxfromscratch.org/lfs/downloads/stable/](http://www.linuxfromscratch.org/lfs/downloads/stable/)
### 下载软件列表：
```shell
jimo@jimo-VirtualBox:/mnt/lfs$ sudo wget www.linuxfromscratch.org/lfs/downloads/stable/wget-list
```
事实证明国内速度非常慢，所以可以另寻方法。
[官网](http://www.linuxfromscratch.org/lfs/download.html)提供了几个镜像地址，可以都试一下，我采用了这个：[http://mirror.jaleco.com/lfs/pub/lfs/lfs-packages/](http://mirror.jaleco.com/lfs/pub/lfs/lfs-packages/)
### 检测软件包
