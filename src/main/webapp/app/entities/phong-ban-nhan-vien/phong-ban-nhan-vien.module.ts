import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { PhongBanNhanVienComponent } from './list/phong-ban-nhan-vien.component';
import { PhongBanNhanVienDetailComponent } from './detail/phong-ban-nhan-vien-detail.component';
import { PhongBanNhanVienUpdateComponent } from './update/phong-ban-nhan-vien-update.component';
import { PhongBanNhanVienDeleteDialogComponent } from './delete/phong-ban-nhan-vien-delete-dialog.component';
import { PhongBanNhanVienRoutingModule } from './route/phong-ban-nhan-vien-routing.module';

@NgModule({
  imports: [SharedModule, PhongBanNhanVienRoutingModule],
  declarations: [
    PhongBanNhanVienComponent,
    PhongBanNhanVienDetailComponent,
    PhongBanNhanVienUpdateComponent,
    PhongBanNhanVienDeleteDialogComponent,
  ],
  entryComponents: [PhongBanNhanVienDeleteDialogComponent],
})
export class PhongBanNhanVienModule {}
