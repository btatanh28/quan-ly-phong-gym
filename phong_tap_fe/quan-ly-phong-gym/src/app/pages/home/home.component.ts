import { ForumService } from './../../../common/shared/service/application/forumService';
import { PostService } from './../../../common/shared/service/application/postService';
import { CommonModule, NgFor } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import Swal from 'sweetalert2';
import { IMAGE_CURRENT } from '../../../common/shared/service/application/api-base';
import { ProductService } from '../../../common/shared/service/application/productService';
import { CartService } from '../../../common/shared/service/application/cartService';
import { DonHangService } from '../../../common/shared/service/application/donhangService';
import { MoneyPipe } from '../../../common/base/pipe/moneny/moneyPipe.component';
import { FormModule } from '../../../common/module/forms.module';
import { FormBuilder, FormGroup } from '@angular/forms';
import { firstValueFrom } from 'rxjs';
import { DateFormatPipe } from '../../../common/base/pipe/dateFormat/dateFormat.component';

@Component({
  selector: 'app-home',
  imports: [NgFor, CommonModule, RouterModule, FormModule, DateFormatPipe],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {
  public myForm?: FormGroup;
  public products: any[] = [];
  public forums: any[] = [];
  public baseUrl = IMAGE_CURRENT;
  public height: number | null = null;
  public weight: number | null = null;
  public bmi: number | null = null;
  public bmiResult: string = '';
  public bmiResultBoolean: boolean = false;
  public listOfData: any[] = [];
  public listOfDataForum: any[] = [];

  constructor(
    private fb: FormBuilder,
    private postService: PostService,
    private forumService: ForumService,
  ) {
    this.myForm = this.fb.group({
      chieuCao: [null],
      canNang: [null],
    });
  }

  async ngOnInit() {
    await this.getData();
  }

  async getData() {
    const res = await firstValueFrom(this.postService.getAllPost({}));
    const response = await firstValueFrom(this.forumService.getAllForum({}));

    this.listOfData = res.items.slice(0, 4);

    this.listOfDataForum = response.items.slice(0, 3);
  }

  calculateBMI() {
    if (
      !this.myForm?.get('chieuCao')?.value ||
      !this.myForm?.get('canNang')?.value
    ) {
      Swal.fire({
        position: 'center',
        icon: 'warning',
        title: 'Thiếu chiều cao hoặc cân nặng',
        showConfirmButton: false,
        timer: 2000,
      });
      return;
    }

    const heightMeter = this.myForm?.get('chieuCao')?.value / 100;
    this.bmi = this.myForm?.get('canNang')?.value / (heightMeter * heightMeter);

    if (this.bmi < 18.5) {
      this.bmiResult = 'Gầy';
    } else if (this.bmi < 25) {
      this.bmiResult = 'Bình thường';
    } else if (this.bmi < 30) {
      this.bmiResult = 'Thừa cân';
    } else {
      this.bmiResult = 'Béo phì';
    }

    this.bmiResultBoolean = true;
  }

  resetBMI() {
    this.myForm?.reset();
    this.bmiResultBoolean = false;
  }
}
