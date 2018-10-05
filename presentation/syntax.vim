" Vim syntax file for custom slides

if exists("b:current_syntax")
  finish
endif

syn keyword types proxy
syn keyword special main POD

hi def link types Type
hi def link special Special
