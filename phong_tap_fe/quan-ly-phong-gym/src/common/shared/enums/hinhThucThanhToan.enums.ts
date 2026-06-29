export enum HinhThucThanhToanEnums {
  THANHTOANCHUYENKHOAN = 1,
  THANHTOANTIENMAT = 2,
}

export const hinhThucThanhToan: { value: number; label: string }[] = [
  {
    value: HinhThucThanhToanEnums.THANHTOANCHUYENKHOAN,
    label: 'Thanh toán chuyển khoản',
  },
  {
    value: HinhThucThanhToanEnums.THANHTOANTIENMAT,
    label: 'Thanh toán tiền mặt',
  },
];
