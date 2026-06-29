export enum TrangThaiDonHangEnums {
  DangXuLy = 1,
  DaThanhToan = 2,
  ChoThanhToan = 3,
  DaHuy = 4,
}

export const trangThaiDonHang: { value: number; label: string }[] = [
  { value: TrangThaiDonHangEnums.DangXuLy, label: 'Đang xử lý' },
  { value: TrangThaiDonHangEnums.DaThanhToan, label: 'Đã thanh toán' },
  { value: TrangThaiDonHangEnums.ChoThanhToan, label: 'Chờ thanh toán' },
  { value: TrangThaiDonHangEnums.DaHuy, label: 'Đã hủy' },
];
