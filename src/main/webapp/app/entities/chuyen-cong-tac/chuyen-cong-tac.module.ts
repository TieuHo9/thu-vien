import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { ChuyenCongTacComponent } from './list/chuyen-cong-tac.component';
import { ChuyenCongTacDetailComponent } from './detail/chuyen-cong-tac-detail.component';
import { ChuyenCongTacUpdateComponent } from './update/chuyen-cong-tac-update.component';
import { ChuyenCongTacDeleteDialogComponent } from './delete/chuyen-cong-tac-delete-dialog.component';
import { ChuyenCongTacRoutingModule } from './route/chuyen-cong-tac-routing.module';

@NgModule({
  imports: [SharedModule, ChuyenCongTacRoutingModule],
  declarations: [ChuyenCongTacComponent, ChuyenCongTacDetailComponent, ChuyenCongTacUpdateComponent, ChuyenCongTacDeleteDialogComponent],
  entryComponents: [ChuyenCongTacDeleteDialogComponent],
})
export class ChuyenCongTacModule {}
