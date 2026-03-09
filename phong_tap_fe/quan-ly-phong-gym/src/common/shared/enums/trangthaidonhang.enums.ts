export enum TrangThaiDonHangEnums {
  DangXuLy = 1,
  DaThanhToan = 2,
}

export const trangThaiDonHang: { value: number; label: string }[] = [
  { value: TrangThaiDonHangEnums.DangXuLy, label: 'Đang xử lý' },
  { value: TrangThaiDonHangEnums.DaThanhToan, label: 'Đã thanh toán' },
];
