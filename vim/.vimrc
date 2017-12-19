" ================1.编码设置================

" 自动语法高亮  
 syntax on  
" 检测文件类型  
filetype on  
" 检测文件类型插件  
filetype plugin on  
" 不设定在插入状态无法用退格键和 Delete 键删除回车符  
set backspace=indent,eol,start  
set whichwrap+=<,>,h,l  
" 显示行号  
set number  
" 上下可视行数  
set scrolloff=6  
" replace tab with space  
set expandtab  
" 设定 tab 长度为 4  
set tabstop=4  
" 设置按BackSpace的时候可以一次删除掉4个空格  
set softtabstop=4  
" 设定 << 和 >> 命令移动时的宽度为 4  
set shiftwidth=4
set smarttab  
set history=1024

" 搜索时高亮显示被找到的文本  
set hlsearch 
" 智能自动缩进  
set smartindent
"显示括号配对情况  
set showmatch
" 解决自动换行格式下, 如高度在折行之后超过窗口高度结果这一行看不到的问题  
set display=lastline
"编码设置  
set encoding=utf-8

" =================2.样式设置==================
" 设定配色方案  
colorscheme molokai

" =================3.自动补全设置==================
" 使用vbundle
set rtp+=~/.vim/bundle/Vundle.vim
call vundle#begin()

" let Vundle manage Vundle, required
Plugin 'VundleVim/Vundle.vim'

" 自动补全插件
Bundle 'Valloric/YouCompleteMe'

call vundle#end()            " required
filetype plugin indent on    " required



