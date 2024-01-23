program logic_flow
    real, parameter :: pi = 3.14159265
    integer, parameter :: n = 100
    real :: result_sin(n)

    real :: angle
    integer :: i
    angle = 91.0

    if (angle < 91.0) then
        print *, 'angle is acute or ever so slgihtly obtuse, possible a right angle'
    else if (angle == 91.0) then
        print *, 'angle is 91!'
    else 
        print *, 'andlge is different'
    end if
    do i = 1, 10, 2
        print *, i ! print the odd ones
    end do
    i=1
    do while (i<100)
        if (mod(i,2)==0 .and. i>10) then
            i=i+1
            cycle
        end if
        print *, i
        if (i > 23) then 
            exit
        end if
        i=i+1
    end do
    ! here i = 25 probalyt

    do concurrent(i=1:n) !syntax not same as non conccurretnt loops
        result_sin(i)=sin(i * pi/4.)
    end do
    print *, result_sin
end program logic_flow