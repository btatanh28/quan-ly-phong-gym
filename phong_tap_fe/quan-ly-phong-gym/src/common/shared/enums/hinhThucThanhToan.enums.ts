export enum HinhThucThanhToanEnums {
  THANHTOANCHUYENKHOANMOMO = 1,
  THANHTOANCHUYENKHOANVNPAY = 2,
  THANHTOANTIENMAT = 3,
}

export const hinhThucThanhToan: { value: number; label: string }[] = [
  {
    value: HinhThucThanhToanEnums.THANHTOANCHUYENKHOANMOMO,
    label: 'Thanh toán chuyển khoản MoMo',
  },
  {
    value: HinhThucThanhToanEnums.THANHTOANCHUYENKHOANVNPAY,
    label: 'Thanh toán chuyển khoản VNPAY',
  },
  {
    value: HinhThucThanhToanEnums.THANHTOANTIENMAT,
    label: 'Thanh toán tiền mặt',
  },
];
