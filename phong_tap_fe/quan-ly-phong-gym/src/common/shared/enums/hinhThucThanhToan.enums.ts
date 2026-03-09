export enum HinhThucThanhToanEnums {
  THANHTOANKHINHANHANG = 1,
  THANHTOANCHUYENKHOAN = 2,
}

export const hinhThucThanhToan: { value: number; label: string }[] = [
  {
    value: HinhThucThanhToanEnums.THANHTOANKHINHANHANG,
    label: 'Thanh toán tiền mặt',
  },
  {
    value: HinhThucThanhToanEnums.THANHTOANCHUYENKHOAN,
    label: 'Thanh toán chuyển khoản',
  },
];
