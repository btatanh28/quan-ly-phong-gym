import { CheckInService } from './../../../../common/shared/service/application/CheckInService';
import { Html5QrcodeScanner } from 'html5-qrcode';
import { Component, OnInit } from '@angular/core';
import { FormModule } from '../../../../common/module/forms.module';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';
import { firstValueFrom } from 'rxjs';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-scan-checkin',
  standalone: true,
  imports: [FormModule, CommonModule],
  templateUrl: './scan-checkin.component.html',
  styleUrls: ['./scan-checkin.component.css'],
})
export class ScanCheckinComponent implements OnInit {
  public formSearch?: FormGroup;
  public qrResult: string = '';
  public scanResult: string = '';

  constructor(
    private checkInService: CheckInService,
    private fb: FormBuilder,
  ) {
    this.formSearch = this.fb.group({
      qrCode: [null],
    });
  }

  async ngOnInit() {
    await this.startScanner();
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

      if (response) {
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
        this.qrResult = '';
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
        this.qrResult = '';
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
}
