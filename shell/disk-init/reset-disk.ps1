<#
used to batch reset disks 
#>

."$PSScriptRoot\config.ps1"

$nums = @($log_number,$data_number,$base_number)


foreach( $n in $nums){
     Clear-Disk -Number $n -RemoveData -Confirm:$false
     sleep(1)
}
