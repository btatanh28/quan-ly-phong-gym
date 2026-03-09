import { ChiTietDonHangService } from './../../../../../common/shared/service/application/chiTietDonHang';
import { DonHangService } from './../../../../../common/shared/service/application/donhangService';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormModule } from '../../../../../common/module/forms.module';
import { CommonModule } from '@angular/common';
import { FormGroup } from '@angular/forms';
import { MoneyPipe } from '../../../../../common/base/pipe/moneny/moneyPipe.component';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-chi-tiet-don-hang',
  standalone: true,
  imports: [FormModule, CommonModule, MoneyPipe],
  templateUrl: './chi-tiet-don-hang.component.html',
  styleUrls: ['./chi-tiet-don-hang.component.css'],
})
export class ChiTietDonHangComponent implements OnInit {
  @Input() mode?: string;
  @Input() id?: any;
  @Output() onClose = new EventEmitter<any | null>();

  public myForm?: FormGroup;
  public listOfData: any[] = [];
  public initForm: boolean = false;
  public indexTab: number = 0;

  constructor(
    private donHangService: DonHangService,
    private chiTietDonHangService: ChiTietDonHangService,
  ) {}

  async ngOnInit() {
    if (this.id) {
      await this.getData();
    }

    this.initForm = true;
  }

  async getData() {
    const response = await firstValueFrom(
      this.chiTietDonHangService.getChiTietDonHangId(this.id),
    );

    this.listOfData = response;
  }

  closeDialog(val: any = null) {
    this.onClose.emit(val);
  }
}
