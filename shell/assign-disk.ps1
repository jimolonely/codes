<#
this script :
1. find the rdm disks(total is 10)
2. to base,new volume named M by formatting it with the chunk size of 64k
3. create 9 directories on Base,8 for data,1 for log
4. to data disks and log disks,new volumn,and format by 64k,assign them to the dirs correspondingly.
#>

# add config
."$PSScriptRoot\config.ps1"


function addDiskToFolder([int]$disk_number,[string]$assign_folder){
    <#
       $disk_number:
       $assign_folder:M:\xxx
    #>
    if($null -eq $disk_number -or $null -eq $assign_folder){
        return
    }
    $Disk = Get-Disk $disk_number
    $Disk | Initialize-Disk -PartitionStyle GPT
    $disk | New-Partition -UseMaximumSize
    $Partition = Get-Partition -DiskNumber $Disk.Number
    $Partition | Format-Volume -FileSystem NTFS -Confirm:$false
    $Partition | Add-PartitionAccessPath -AccessPath $assign_folder
}

function addDiskAndFormat([int]$number,[string]$driverLetter){
    if($null -eq $number){
        return
    }
    $Disk = Get-Disk $number
    $Disk | Initialize-Disk -PartitionStyle GPT
    $disk | New-Partition -UseMaximumSize  -DriveLetter $driverLetter
    $Partition = Get-Partition -DiskNumber $Disk.Number
    $Partition | Format-Volume -FileSystem NTFS -AllocationUnitSize 64kb -Confirm:$false
}

#1.add disk base named M
addDiskAndFormat $base_number 'M'
sleep(1)

#2.create folders and assign disk to folder
$log_folder = 'M:\Clarity_ReportLog\mnt'
mkdir $log_folder -Force
addDiskToFolder $log_number $log_folder

$data_folders = @()
$dataCount = $data_number.Count
$i = 1
foreach($n in $data_number){
    $df = 'M:\Clarity_Report'+$i+'\mnt'
    mkdir $df -Force
    addDiskToFolder $n $df
    $i = $i + 1
}



