program read_value
    use, intrinsic :: iso_fortran_env, only: sp=>real32, dp=>real64
    implicit none
    integer :: age
    real :: pi
    real :: radius
    real :: height
    real :: area
    real :: volume
    real(sp) :: float32
    real(dp) :: float64
    ! these must be at the top????!!!!!



    float32 = 1.0_sp ! explicit suffix for literal constants
    float64 = 1.0_dp
    print *, 'Please enter your age: '
    read(*,*) age
  
    print *, 'Your age is: ', age
  
    ! maths example
  

    pi = 3.1415927
    print *, 'Enter the cylinders base radius: '
    read(*,*) radius

    print *, 'enter ecylinder height pls: '
    read(*,*) height

    area = pi * radius**2 
    ! thats an exponenet
    volume = area * height

    print *, 'Cylindr radius is ', radius
    print *, 'Cylinder height is: ', height
    print *, 'cylinder base area is: ', area
    print *, 'cylinder vol is: ', volume
  end program read_value