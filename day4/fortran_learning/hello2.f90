program arrays
    implicit none

    ! 1drection array
    integer :: i
    integer, dimension(10) :: array1
    !equivalent array declaration
    integer :: array2(10,10)
    integer, allocatable :: array1a(:)
    integer, allocatable :: array2a(:,:)
    ! 2d vs 1d array allocatiionable 

    !2 dim array
    real, dimension(10,10) :: array3

    ! custom lower, upper bounds?
    real :: array4(0:9)
    real :: array5(-5:5)

    !strings?
    character(len=4) :: first_name
    character(len=5) :: last_name
    character(10) :: full_name
    character(:), allocatable :: firs_name
    character(:), allocatable :: las_name

    allocate(character(20)::firs_name)
    read(*,*) firs_name
    las_name = 'Logan!!'
    print *, firs_name//' '//las_name
    first_name = 'Chrs'
    last_name = 'Logan'

    !str cioncationation!
    full_name = first_name//' '//last_name

    allocate(array1a(10)) ! allocate 10 to array1a
    allocate(array2a(10,10))

    array1 = [1,2,3,4,5,6,7,8,9,10] ! array constructor one?
    array1 = [(i,i=1,10)] ! implied "do" loop constructor
    array1(:) = 0 ! set errything to ero
    array1(1:5) = 1 ! set forst 5 elements to one
    array1(6:) = 1 ! set everything after 5 to 1
    print *, array1(1:10:2) ! print out every 2 starting at 1.
    print *, array2(:,1) ! print out first col in 2d array
    print *, array1(10:1:-1) ! print out array in reverse ordr
    print *, full_name
    deallocate(array1a)
    deallocate(array2a)

end program arrays