# SIO Project Deployment - Azure

## Description

This repository contains the necessary files and instructions to run OpenKM (v6.3.9) and a MySQL database (v5.7.16) in a Azure virtual machine, using Docker as the conteinerization technology.

## Setting up the VM on Azure

- Create a virutal machine on Azure. We used a preset on Azure, that automatically configured the VM, but also a public IP address, a network security group, a virtual network, a network interface and a disk.
  
  - For our VM, we choose the size Standard B1ms, which has 1 vCPU and 2 GiB of memory. It was the minimum requirements that we needed to have for our deployment to run smoothly.

- Create an SSH key to connect to the VM using SSH. Get the private key .pem file, and place it on your home directory (~).

- Make your working directory this repository's root, and considering `sio-project` as the name of the VM, run the following command to copy the files into the VM (replace `PUBLIC_IP` with the public IP address of the VM).
  
  ```shell
  sudo scp -i ~/sio-project.pem -r $(pwd) sio-project@PUBLIC_IP:/home/sio-project/openkm
  ```

- Connect to the VM, run the following command.
  
  ```shell
  sudo ssh sio-project@PUBLIC_IP -i ~/sio-project.pem
  ```

- Inside the VM, run the following commands to install Docker.
  
  ```shell
  sudo apt-get update
  sudo apt-get install ca-certificates curl
  sudo install -m 0755 -d /etc/apt/keyrings
  sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc
  sudo chmod a+r /etc/apt/keyrings/docker.asc
  echo \
    "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/ubuntu \
    $(. /etc/os-release && echo "$VERSION_CODENAME") stable" | \
    sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
  sudo apt-get update
  sudo apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
  ```

- Finally, move to the `openkm` directory and start the containers.
  
  ```shell
  cd openkm
  sudo docker compose up -d
  ```

## Accessing OpenKM

If everything was well configured, an OpenKM instance should be accessible on any browser through the VM's public IP address or DNS name (in our case `http://sio-project.northeurope.cloudapp.azure.com/`).

## Admin credentials

An admin account is configured by default, with the following credentials.

```
user: okmAdmin
pass: admin
```

## Authors

- Daniel Ferreira, 102885

- Diogo Paiva, 103183

- João Fonseca, 103154
