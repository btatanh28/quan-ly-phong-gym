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
  selectedMonth: number | '' = '';

  constructor(
    private fb: FormBuilder,
    private donHangService: DonHangService,
    private chiTietDonHangService: ChiTietDonHangService,
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

    this.donHangService.getDoanhThu(params).subscribe(async (res: any) => {
      await this.drawChart(res.items);
    });

    const res = await firstValueFrom(
      this.chiTietDonHangService.getChiTietDoanhThu(params),
    );

    this.listOfData = res.items;
  }

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

    const ctx = document.getElementById('revenueChart') as HTMLCanvasElement;

    if (this.chart) this.chart.destroy();

    this.chart = new Chart(ctx, {
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
                return ' ' + context.raw.toLocaleString('vi-VN') + ' VNĐ';
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

  exportExcel() {
    const params = {
      ...this.formSearch?.value,
    };

    this.donHangService.exportDoanhThu(params).subscribe({
      next: (res: any) => {
        const blob = new Blob([res], {
          type: 'text/csv;charset=utf-8;',
        });

        const url = window.URL.createObjectURL(blob);

        const a = document.createElement('a');
        a.href = url;
        a.download = 'doanh-thu.csv';
        a.click();

        window.URL.revokeObjectURL(url);
      },

      error: (err) => {
        console.log(err);

        let message = 'Lỗi khi export';

        if (err?.error instanceof Blob) {
          // 🔥 đọc message từ BE trả về
          const reader = new FileReader();
          reader.onload = () => {
            Swal.fire({
              position: 'center',
              icon: 'error',
              title: 'Không có dữ liệu doanh thu',
              showConfirmButton: false,
              timer: 2000,
            });
          };
          reader.readAsText(err.error);
        } else {
          alert(err?.error?.message || message);
        }
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
