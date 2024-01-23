subroutine print_matrix(n, m, A)
    implicit none

    integer, intent(in) :: n
    integer, intent(in) :: m
    real, intent(in) :: A(n,m)

    integer :: i

    do i = 1, n
        print *, A(i, 1:m)
    end do
end subroutine print_matrix

function vector_norm(n, vec) result(norm)
    implicit none
    integer, intent(in) :: n
    real, intent(in) :: vec(n)

    real:: norm

    norm = sqrt(sum(vec**2))
end function vector_norm

program code_org
    use my_mod, only: print_matrix2=>print_matrix
    implicit none 

    real :: mat(10,20)
    real :: v(9)
    real :: vector_norm

    v(:) = 9
    mat(:,: ) = 0.0

    call print_matrix2(mat)
    print *, 'vector norm = ', vector_norm(9, v)

end program code_org
