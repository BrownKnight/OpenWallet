import Vue from "vue";
import moment from "moment";

export class BaseComponent extends Vue {
  showMessage({ message, variant, delay }: { message: string; variant?: string; delay?: number }) {
    this.$bvToast.toast(message, {
      noCloseButton: true,
      variant: variant,
      autoHideDelay: delay ?? 3000,
      toaster: "b-toaster-top-right",
      href: "#"
    });
  }

  isAdmin(): boolean {
    return false;
  }

  prettyFormatDate(date: string | Date): string {
    return moment(date).format("ddd, Do MMM");
  }

  prettyFormatTime(time: string | Date): string {
    return moment(time, "HH:mm").format("HH:mm");
  }

  prettyFormatDateTime(datetime: string | Date): string {
    return moment(datetime).format("DD Oct, HH:mm");
  }
}
