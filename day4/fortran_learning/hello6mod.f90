module m_shapes
    implicit none
    private
    public t_square

    type :: t_square
        real :: side
        contains
          procedure :: area ! procedure declaration!!!
    end type

    contains

    subroutine area(self, x)
        class(t_square), intent(in) :: self
        real, intent(out) :: x
        x = self%side**2
        end subroutine
    !real function area(self) result(res)
    !  class(t_square), intent(in) :: self
    !  res = self%side**2
   ! end function
end module m_shapes