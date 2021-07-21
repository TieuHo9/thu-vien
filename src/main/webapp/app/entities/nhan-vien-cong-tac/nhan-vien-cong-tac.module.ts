import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { NhanVienCongTacComponent } from './list/nhan-vien-cong-tac.component';
import { NhanVienCongTacDetailComponent } from './detail/nhan-vien-cong-tac-detail.component';
import { NhanVienCongTacUpdateComponent } from './update/nhan-vien-cong-tac-update.component';
import { NhanVienCongTacDeleteDialogComponent } from './delete/nhan-vien-cong-tac-delete-dialog.component';
import { NhanVienCongTacRoutingModule } from './route/nhan-vien-cong-tac-routing.module';

@NgModule({
  imports: [SharedModule, NhanVienCongTacRoutingModule],
  declarations: [
    NhanVienCongTacComponent,
    NhanVienCongTacDetailComponent,
    NhanVienCongTacUpdateComponent,
    NhanVienCongTacDeleteDialogComponent,
  ],
  entryComponents: [NhanVienCongTacDeleteDialogComponent],
})
export class NhanVienCongTacModule {}
