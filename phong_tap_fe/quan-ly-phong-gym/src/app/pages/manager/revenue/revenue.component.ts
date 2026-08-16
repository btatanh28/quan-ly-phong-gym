import { CustomerService } from './../../../../common/shared/service/application/customerService';
import { ChiTietDonHangService } from './../../../../common/shared/service/application/chiTietDonHang';
import { DonHangService } from './../../../../common/shared/service/application/donhangService';
import { Component, OnInit } from '@angular/core';
import { InputSelectComponent } from '../../../../common/base/controls/input-select/input-select.component';
import { ngayThangNamEnums } from '../../../../common/shared/enums/ngaythangnam.enums';
import { FormModule } from '../../../../common/module/forms.module';
import { FormBuilder, FormGroup } from '@angular/forms';
import Chart from 'chart.js/auto';
import { firstValueFrom } from 'rxjs';
import { MoneyPipe } from '../../../../common/base/pipe/moneny/moneyPipe.component';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-revenue',
  templateUrl: './revenue.component.html',
  styleUrls: ['./revenue.component.css'],
  imports: [InputSelectComponent, FormModule, MoneyPipe],
})
export class RevenueComponent implements OnInit {
  public formSearch?: FormGroup;
  public listOfData: any[] = [];
  public listNgayThangNam: any[] = ngayThangNamEnums;
  public chart: any;
  public selectedMonth: number | '' = '';
  public tongDoanhThu: number = 0;
  public tongKhachHang: number = 0;
  public tongSoDon: number = 0;
  public trungBinhDoanhThuDon: number = 0;

  constructor(
    private fb: FormBuilder,
    private donHangService: DonHangService,
    private chiTietDonHangService: ChiTietDonHangService,
    private customerService: CustomerService,
  ) {
    this.formSearch = this.fb.group({
      year: [null],
      month: [null],
    });
  }

  async ngOnInit() {
    this.formSearch?.get('year')?.setValue(new Date().getFullYear());
    await this.getData();
  }

  async getData() {
    const params = {
      ...this.formSearch?.value,
    };

    // Tổng số khách hàng
    this.customerService.getAllKhachHang('').subscribe((res: any) => {
      this.tongKhachHang = res.items.length;
    });

    // Doanh thu + biểu đồ + đơn đăng ký
    this.donHangService.getDoanhThu(params).subscribe(async (res: any) => {
      await this.drawChart(res.items);

      this.tongDoanhThu = res.items.reduce(
        (total: number, item: any) =>
          total + Number(item.tongTienDoanhThu || 0),
        0,
      );

      const items = res.items ?? [];

      this.tongSoDon = items.reduce(
        (total: number, item: any) => total + Number(item.tongSoDon || 0),
        0,
      );

      this.trungBinhDoanhThuDon =
        this.tongSoDon > 0 ? Math.round(this.tongDoanhThu / this.tongSoDon) : 0;
    });

    // Chi tiết gói tập
    this.chiTietDonHangService
      .getChiTietDoanhThu(params)
      .subscribe(async (res: any) => {
        await this.drawChartChiTiet(res.items);

        this.listOfData = res.items;
      });
  }

  public revenueChart: any;
  public chiTietChart: any;

  drawChart(data: any[]) {
    const year = this.formSearch?.get('year')?.value;
    const month = this.formSearch?.get('month')?.value;

    let labels: any[] = [];

    // 🔥 Logic labels
    if (month) {
      // có tháng → hiển thị theo ngày
      labels = data.map((x) => `Ngày ${x.ngay}`);
    } else if (year) {
      // chỉ có năm → hiển thị theo tháng
      labels = data.map((x) => `Tháng ${x.thang}`);
    } else {
      // không chọn gì → hiển thị theo năm
      labels = data.map((x) => `Năm ${x.nam}`);
    }

    const values = data.map((x) => x.tongTienDoanhThu);
    const tongSoDon = data.map((x) => x.tongSoDon);

    const ctx = document.getElementById('revenueChart') as HTMLCanvasElement;

    if (this.revenueChart) {
      this.revenueChart.destroy();
    }

    this.revenueChart = new Chart(ctx, {
      type: 'bar',
      data: {
        labels,
        datasets: [
          {
            label: 'Doanh thu',
            data: values,
            backgroundColor: '#0d6efd',
            borderRadius: 4,
            borderSkipped: false,
            hoverBackgroundColor: '#0d6efd',
          },
        ],
      },
      options: {
        responsive: true,
        plugins: {
          legend: {
            display: true,
            labels: {
              color: '#333',
              font: {
                size: 14,
                weight: 'bold',
              },
            },
          },
          tooltip: {
            callbacks: {
              label: (context: any) => {
                const index = context.dataIndex;

                const doanhThu = values[index] ?? 0;
                const soDon = tongSoDon[index] ?? 0;

                return [
                  ` Doanh thu: ${doanhThu.toLocaleString('vi-VN')} VNĐ`,
                  ` Số đơn: ${soDon}`,
                ];
              },
            },
          },
        },
        scales: {
          x: {
            ticks: {
              color: '#666',
            },
            grid: {
              display: false,
            },
          },
          y: {
            ticks: {
              color: '#666',
              callback: function (value: any) {
                return value.toLocaleString('vi-VN');
              },
            },
            grid: {
              color: '#eee',
            },
          },
        },
      },
    });
  }

  drawChartChiTiet(data: any[]) {
    const labels = data.map((x) => x.tenGoiTap);
    const values = data.map((x) => x.soLuong);

    const ctx = document.getElementById(
      'revenueChartChiTiet',
    ) as HTMLCanvasElement;

    if (this.chiTietChart) {
      this.chiTietChart.destroy();
    }

    this.chiTietChart = new Chart(ctx, {
      type: 'pie',

      data: {
        labels,

        datasets: [
          {
            label: 'Số lượng gói tập',
            data: values,

            // Mỗi gói tập một màu
            backgroundColor: [
              '#0d6efd',
              '#198754',
              '#ffc107',
              '#dc3545',
              '#6f42c1',
              '#20c997',
              '#fd7e14',
              '#d63384',
              '#6610f2',
              '#0dcaf0',
            ],

            borderWidth: 2,
            borderColor: '#fff',

            hoverOffset: 8,
          },
        ],
      },

      options: {
        responsive: true,

        maintainAspectRatio: false,

        plugins: {
          legend: {
            display: true,
            position: 'bottom',

            labels: {
              color: '#333',

              font: {
                size: 14,
                weight: 'bold',
              },

              usePointStyle: true,
              pointStyle: 'circle',

              padding: 15,
            },
          },

          tooltip: {
            callbacks: {
              label: (context: any) => {
                const value = Number(context.raw);

                const total = context.dataset.data.reduce(
                  (sum: number, item: any) => sum + Number(item),
                  0,
                );

                const percent =
                  total > 0 ? ((value / total) * 100).toFixed(1) : 0;

                return ` ${context.label}: ${value} gói (${percent}%)`;
              },
            },
          },
        },
      },
    });
  }

  exportExcel() {
    const params = {
      ...this.formSearch?.value,
    };

    this.donHangService.exportDoanhThu(params).subscribe({
      next: (res: Blob) => {
        const blob = new Blob([res], {
          type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
        });

        const url = window.URL.createObjectURL(blob);

        const a = document.createElement('a');

        a.href = url;
        a.download = 'doanh-thu.xlsx';

        a.click();

        window.URL.revokeObjectURL(url);
      },

      error: (err) => {
        Swal.fire({
          position: 'center',
          icon: 'error',
          title: 'Không có dữ liệu doanh thu',
          showConfirmButton: false,
          timer: 2000,
        });
      },
    });
  }

  async onReset(init: boolean = false) {
    this.formSearch?.reset();

    this.formSearch?.patchValue({
      year: new Date().getFullYear(),
      month: null,
    });

    if (!init) {
      await this.getData();
    }
  }
}
