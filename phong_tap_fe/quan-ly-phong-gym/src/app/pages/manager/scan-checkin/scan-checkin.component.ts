import { CheckInService } from './../../../../common/shared/service/application/CheckInService';
import { Html5QrcodeScanner } from 'html5-qrcode';
import { Component, OnInit } from '@angular/core';
import { FormModule } from '../../../../common/module/forms.module';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';
import { firstValueFrom } from 'rxjs';
import { FormBuilder, FormGroup } from '@angular/forms';
import { InputDateTimeComponent } from '../../../../common/base/controls/input-date-time/input-date-time.component';
import { DateTimeFormatPipe } from '../../../../common/base/pipe/dateTimeFormat/dateTimeForm.component';

interface CheckInResponse {
  qrCode: string;
  tenKhachHang: string;
}

@Component({
  selector: 'app-scan-checkin',
  standalone: true,
  imports: [
    FormModule,
    CommonModule,
    DateTimeFormatPipe,
  ],
  templateUrl: './scan-checkin.component.html',
  styleUrls: ['./scan-checkin.component.css'],
})
export class ScanCheckinComponent implements OnInit {
  public formSearch?: FormGroup;
  public formSearchLichSu?: FormGroup;
  public qrResult: CheckInResponse | null = null;
  public scanResult: string = '';
  public listOfData: any[] = [];
  public activeTab: string = 'Scan';
  public loadedLichSu = false;

  constructor(
    private checkInService: CheckInService,
    private fb: FormBuilder,
  ) {
    this.formSearch = this.fb.group({
      qrCode: [null],
      tenKhachHang: [null],
    });

    this.formSearchLichSu = this.fb.group({
      id: [null],
      qrCode: [null],
      tenKhachHang: [null],
      soDienThoai: [null],
      thoiGian: [null],
    });
  }

  async ngOnInit() {
    await this.selectTab(this.activeTab);
  }

  async selectTab(tab: string) {
    this.activeTab = tab;

    if (tab === 'Scan') {
      this.loadedLichSu = false;

      setTimeout(() => {
        this.startScanner();
      }, 300);
    }

    if (tab === 'Lịch sử' && !this.loadedLichSu) {
      await this.getData();

      this.loadedLichSu = true;
    }
  }

  async getData() {
    if (!this.formSearchLichSu) return;

    const params = {
      ...this.formSearchLichSu.value,
    };

    const res = await firstValueFrom(
      this.checkInService.getAllLichSuCheckIn(params),
    );

    this.listOfData = res.items;
  }

  async startScanner() {
    const scanner = new Html5QrcodeScanner(
      'reader',
      { fps: 10, qrbox: 250 },
      false,
    );

    scanner.render(
      (decodedText: string) => {
        if (this.scanResult) return;

        this.scanResult = decodedText;

        scanner.clear();

        this.checkin(decodedText);
      },
      (error: any) => {
        console.warn('QR error:', error);
      },
    );
  }

  async checkInScan() {
    try {
      const body = {
        qrCode: this.formSearch?.get('qrCode')?.value,
        thietBi: 'Barcode Scanner',
      };

      const response = await firstValueFrom(this.checkInService.checkIn(body));

      if (response.data) {
        Swal.fire({
          position: 'center',
          icon: 'success',
          title: 'Check-in thành công',
          showConfirmButton: false,
          timer: 2000,
        });

        if (this.qrResult) return;

        this.qrResult = response.data;
      }

      setTimeout(() => {
        this.qrResult = null;
        this.formSearch?.reset();
      }, 5000);
    } catch (error) {
      Swal.fire({
        position: 'center',
        icon: 'error',
        title: 'Hôm nay đã check-in',
        showConfirmButton: false,
        timer: 2000,
      });

      setTimeout(() => {
        this.qrResult = null;
        this.formSearch?.reset();

        const input = document.getElementById('qrCode');
        input?.focus();
      }, 100);
    }
  }

  async checkin(code: string) {
    try {
      const body = {
        qrCode: code,
        thietBi: 'WEB_CAMERA',
      };

      const response = await firstValueFrom(this.checkInService.checkIn(body));
      if (response) {
        Swal.fire({
          position: 'center',
          icon: 'success',
          title: 'Check-in thành công',
          showConfirmButton: false,
          timer: 2000,
        });

        setTimeout(() => {
          this.startScanner();
          this.scanResult = '';
        }, 3000);
      }
    } catch (error) {
      Swal.fire({
        position: 'center',
        icon: 'error',
        title: 'Hôm nay đã check-in',
        showConfirmButton: false,
        timer: 2000,
      });

      setTimeout(() => {
        this.startScanner();
        this.scanResult = '';
      }, 2000);
    }
  }

  async onReset(init: boolean = false) {
    this.formSearch?.reset();
    this.formSearchLichSu?.reset();

    if (!init) {
      await this.getData();
    }
  }

  changeTabLichSu(val: any) {}
}
