export enum GiamGiaEnums {
  NAMPHANTRAM = 1,
  MUOIPHANTRAM = 2,
  MUOILAMPHANTRAM = 3,
  HAIMUOIPHANTRAM = 4,
  HAILAMPHANTRAM = 5,
  NAMMUOIPHANTRAM = 6,
}

export const giamGia: { value: number; label: string }[] = [
  { value: GiamGiaEnums.NAMPHANTRAM, label: '5%' },
  { value: GiamGiaEnums.MUOIPHANTRAM, label: '10%' },
  { value: GiamGiaEnums.MUOILAMPHANTRAM, label: '15%' },
  { value: GiamGiaEnums.HAIMUOIPHANTRAM, label: '20%' },
  { value: GiamGiaEnums.HAILAMPHANTRAM, label: '25%' },
  { value: GiamGiaEnums.NAMMUOIPHANTRAM, label: '50%' },
];
