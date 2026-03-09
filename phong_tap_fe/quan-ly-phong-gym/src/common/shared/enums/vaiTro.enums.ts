export enum VaiTroEnums {
  Admin = 1,
  QuanLy = 2,
  KeToan = 3,
  NhanVien = 4,
  HuanLuyenGym = 5,
  HuyenLuyenYoga = 6,
}

export const vaiTroOptions: { value: number; label: string }[] = [
  { value: VaiTroEnums.Admin, label: 'Admin' },
  { value: VaiTroEnums.QuanLy, label: 'Quản lý' },
  { value: VaiTroEnums.KeToan, label: 'Kế toán' },
  { value: VaiTroEnums.NhanVien, label: 'Nhân viên' },
  { value: VaiTroEnums.HuanLuyenGym, label: 'Huấn luyện viên Gym' },
  { value: VaiTroEnums.HuyenLuyenYoga, label: 'Huấn luyện viên Yoga' },
];
