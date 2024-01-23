module my_mod
    implicit none
    private ! all entites are now module-private by default
    public public_var, print_matrix !explicitly export public entities!

    real, parameter :: public_var = 2
    integer :: private_var

    contains
    ! print matrix A to screen poorely
    subroutine print_matrix(A)
        real, intent(in) :: A(:,:) ! an assumed shape dummy
        integer :: i
        do i = 1, size(A,1)
            print *, A(i,:)
        end do
    end subroutine print_matrix
end module my_mod