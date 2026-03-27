export enum XacNhanEnums {
  CHUAKICHHOAT = 0,
  DAKICHHOAT = 1,
}

export const xacNhan: { value: number; label: string }[] = [
  { value: XacNhanEnums.CHUAKICHHOAT, label: 'Chưa kích hoạt' },
  { value: XacNhanEnums.DAKICHHOAT, label: 'Đã kích hoạt' },
];
