program types_stuff
    use m_shapes
    implicit none
    type(t_square) :: sq
    real :: x, side
    type :: t_pair
        integer :: i
        real :: x
    end type
    type :: t_pair2
        integer ::i = 2
        real :: x = 0.8
    end type
    type(t_pair2) ::pair2 ! init to 2, 0.8 by def
    type(t_pair) :: pair

    pair%i = 1
    pair%x = 0.5
    pair2 = t_pair2()
    pair2 = t_pair2(i=22) ! i=22, x=0.8
    pair = t_pair(1, 0.5)
    pair = t_pair(i = 1, x = 0.5)
    pair = t_pair (x= 10.0, i  = 12)
    ! positional vs keywords, vs keyword in "different" order

! more_structs??? see the extends stuff for more fun: https://fortran-lang.org/learn/quickstart/derived_types/

    side = 0.5
    sq%side = side
    
    
    ! x = sq%area() ! if area is a function
    ! if area is subroutine:
    call sq%area(x) ! x is qhere result ends up
    ! self does not appear here it was passed implicitly!
    print *, x
end program types_stuff