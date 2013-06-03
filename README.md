A tool to forward notifications to Google Glass from a tethered phone.

This uses the disabled MyGlass API: to enable it:

 - For the tethered phone: get a modified MyGlass APK at http://zhuowei.github.io/Xenologer/myglass-modded.apk

 - For the Glass: Enable the lab for it with root. Modify build.prop so `ro.build.type` becomes `userdebug` (Google is your friend)
then type in a root shell `setprop persist.lab.companion_api true` to activate the experiment.

 - For devices running the Xenologer APKs: type in a root shell `setprop persist.lab.companion_api true` to activate the experiment.

Then, enable NotifyTooth from Settings->Accessibility of the tethered phone.

There's an anti-spam cooldown: if an app shows another notification less than a second after showing the first one, the second will be dropped.

Thanks to https://code.google.com/p/gtalksms/ for inspiration and a bit of code.

Download: http://zhuowei.github.io/NotifyTooth/NotifyTooth-release.apk
