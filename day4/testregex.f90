program test_program2  ! m.m. modifed
    use regex_module
    implicit none
  
    integer, parameter        :: nmatch     = 10
    integer, parameter        :: linelength = 200
  
    logical                   :: match
    integer                   :: i, istatus, matches(2,nmatch)
    type(regex_type)          :: regex
    character(len=linelength) :: inlin, regc, found
  
    write(*,'(a)',advance="no")"regexp: "
    read (*,'(a)',end=999     ) regc
    DO
      write(*,'(a)',advance="no") "text: "
      read( *,'(a)',end=999     ) inlin
      call regcomp(regex,trim(regc),'xm')       ! trim --> blanks at the end must be specified as [ ]
      match = regexec(regex,trim(inlin),matches,status=istatus)
      FORALL(i=1:len(found))found(i:i)="-"
      DO i=1,nmatch
        if(matches(1,i) == NO_MATCH) exit   
        write(*,*) 'match=',matches(:,i),'"',regmatch(i,trim(inlin),matches),'"'
        found(matches(1,i)+1:matches(2,i)) = regmatch(i,trim(inlin),matches)
      ENDDO
      
      write(*,*)
      write(*,'(a)')trim(inlin)
      write(*,'(a)')trim(found(1:len_trim(inlin)))
      
    ENDDO
  
    call regfree(regex)
  
  999 stop
  end program test_program2