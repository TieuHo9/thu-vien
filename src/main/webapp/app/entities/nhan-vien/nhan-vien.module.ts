import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { NhanVienComponent } from './list/nhan-vien.component';
import { NhanVienDetailComponent } from './detail/nhan-vien-detail.component';
import { NhanVienUpdateComponent } from './update/nhan-vien-update.component';
import { NhanVienDeleteDialogComponent } from './delete/nhan-vien-delete-dialog.component';
import { NhanVienRoutingModule } from './route/nhan-vien-routing.module';

@NgModule({
  imports: [SharedModule, NhanVienRoutingModule],
  declarations: [NhanVienComponent, NhanVienDetailComponent, NhanVienUpdateComponent, NhanVienDeleteDialogComponent],
  entryComponents: [NhanVienDeleteDialogComponent],
})
export class NhanVienModule {}
