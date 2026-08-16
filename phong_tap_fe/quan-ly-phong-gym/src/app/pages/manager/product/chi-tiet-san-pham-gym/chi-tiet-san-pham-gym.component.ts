import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormModule } from '../../../../../common/module/forms.module';
import { CommonModule } from '@angular/common';
import { InputMonenyComponent } from '../../../../../common/base/controls/input-moneny/input-moneny.component';
import { InputSelectComponent } from '../../../../../common/base/controls/input-select/input-select.component';
import { FormBuilder, FormGroup } from '@angular/forms';
import { giamGia } from '../../../../../common/shared/enums/giamGia.enums';
import {
  DialogMode,
  DialogService,
} from '../../../../../common/shared/service/base/dialogservice';
import { ExtentionService } from '../../../../../common/base/service/extention.service';
import { firstValueFrom } from 'rxjs';
import Swal from 'sweetalert2';
import { SanPhamGymService } from '../../../../../common/shared/service/application/sanPhamGymService';
import { API_CURRENT } from '../../../../../common/shared/service/application/api-base';

@Component({
  selector: 'app-chi-tiet-san-pham-gym',
  standalone: true,
  imports: [
    FormModule,
    CommonModule,
    InputMonenyComponent,
    InputSelectComponent,
  ],
  templateUrl: './chi-tiet-san-pham-gym.component.html',
  styleUrls: ['./chi-tiet-san-pham-gym.component.css'],
})
export class ChiTietSanPhamGymComponent implements OnInit {
  @Input() mode?: string;
  @Input() id?: any;
  @Output() onClose = new EventEmitter<any | null>();

  public previewUrl: string | ArrayBuffer | null = null;
  public myForm?: FormGroup;
  public initForm: boolean = false;
  public indexTab: number = 0;
  public viewButtonSave: boolean = false;
  public listOfData: any[] = [];
  public modalImg: boolean = true;
  public listGiamGia: any[] = giamGia;

  constructor(
    private dialogService: DialogService,
    private fb: FormBuilder,
    private ex: ExtentionService,
    private sanPhamGymService: SanPhamGymService,
  ) {
    this.myForm = this.fb.group({
      id: [this.ex.newGuid()],
      tenSanPham: [null],
      gia: [null],
      hinhAnh: [null],
      moTa: [null],
      soTonKho: [null],
      giamGia: [null],
    });
  }

  async ngOnInit() {
    if (this.id) {
      await this.getData();
    }

    await this.handleModeDialog();

    this.initForm = true;
  }

  async handleModeDialog() {
    const modeDisableForm = [DialogMode.view];

    if (this.mode && modeDisableForm.includes(this.mode as DialogMode)) {
      this.myForm?.disable();
    }

    if (this.mode === DialogMode.add || this.mode === DialogMode.edit) {
      this.viewButtonSave = true;
    }
  }

  async getData() {
    const response = await firstValueFrom(
      this.sanPhamGymService.getSanPhamById(this.id),
    );
    if (response) {
      this.myForm?.patchValue(response);
      if (response.hinhAnh) {
        this.previewUrl = API_CURRENT + response.hinhAnh;
        this.modalImg = false;
      }
    }
  }

  async saveData() {
    const giaValue = this.myForm?.get('gia')?.value;
    if (!giaValue) {
      Swal.fire({
        position: 'center',
        icon: 'warning',
        title: 'Thiếu giá sản phẩm',
        showConfirmButton: false,
        timer: 2000,
      });
      return;
    }

    const req = {
      ...this.myForm?.getRawValue(),
    };

    let response = null;

    if (this.mode === DialogMode.add) {
      response = await firstValueFrom(this.sanPhamGymService.addSanPham(req));
    } else {
      response = await firstValueFrom(
        this.sanPhamGymService.updateSanPham(req),
      );
    }
    Swal.fire({
      title: 'Lưu dữ liệu thành công!',
      icon: 'success',
      draggable: false,
    });

    this.closeDialog(true);
  }

  closeDialog(val: any = null) {
    this.onClose.emit(val);
  }

  onFileSelected(event: any) {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();

      reader.onload = () => {
        this.previewUrl = reader.result;

        this.myForm?.patchValue({
          hinhAnh: reader.result, // 👈 gửi ảnh base64 trong JSON
        });

        this.modalImg = false;
      };

      reader.readAsDataURL(file); // chuyển thành base64
      this.modalImg = false;
    }
  }
}
