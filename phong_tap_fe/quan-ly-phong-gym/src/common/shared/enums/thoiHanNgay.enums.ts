export enum ThoiHanNgayEnums {
  MOTNGAY = 1,
  HAITHANG = 2,
  BATHANG = 3,
  SAUTHANG = 4,
  MOTNAM = 5,
  HAINAM = 6,
}

export const thoiHanNgay: { value: number; label: string }[] = [
  { value: ThoiHanNgayEnums.MOTNGAY, label: '1 ngày' },
  { value: ThoiHanNgayEnums.HAITHANG, label: '2 tháng' },
  { value: ThoiHanNgayEnums.BATHANG, label: '3 tháng' },
  { value: ThoiHanNgayEnums.SAUTHANG, label: '6 tháng' },
  { value: ThoiHanNgayEnums.MOTNAM, label: '1 năm' },
  { value: ThoiHanNgayEnums.HAINAM, label: '2 năm' },
];
