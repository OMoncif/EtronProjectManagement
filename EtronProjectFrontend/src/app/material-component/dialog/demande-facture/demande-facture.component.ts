import { Component, EventEmitter, Inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { EtronService } from 'src/app/services/etron.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { GlobalConstants } from 'src/app/shared/global-constants';
import { CategoryComponent } from '../category/category.component';

@Component({
  selector: 'app-demande-facture',
  templateUrl: './demande-facture.component.html',
  styleUrls: ['./demande-facture.component.scss']
})
export class DemandeFactureComponent {

  onAddFacture = new EventEmitter();
  factureForm: any = FormGroup;
  dialogAction: any = "Add";
  action: any = "Add";

  responseMessage: any;
  constructor(@Inject(MAT_DIALOG_DATA) public dialogData: any,
    private formBuilder: FormBuilder,
    private etronService: EtronService,
    public dialogRef: MatDialogRef<CategoryComponent>,
    private snackbarService: SnackbarService) { }

  ngOnInit(): void {
    this.factureForm = this.formBuilder.group({
      mois: [null, [Validators.required]],
      annee: [null, [Validators.required]]
    });
    if (this.dialogData.action === 'Edit') {
      this.dialogAction = "Edit";
      this.action = "Update"
      this.factureForm.patchValue(this.dialogData.data);
    }
  }

  handleSubmit() {

    this.add();

  }

  add() {
    var formData = this.factureForm.value;
    var data = {
      mois: formData.mois,
      annee : formData.annee
    }
    this.etronService.demanderFacture(data).subscribe((response: any) => {
      this.dialogRef.close();
      this.onAddFacture.emit();
      this.responseMessage = response.message;
      this.snackbarService.openSnackBar(this.responseMessage, "success");
    }, (error) => {
      this.dialogRef.close();
      console.error(error);
      if (error.error?.message) {
        this.responseMessage = error.error?.message;
      }
      else {
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
    });
  }

}
